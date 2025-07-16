library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
use IEEE.std_logic_arith.ALL;

entity EX is
Port (RD1: in std_logic_vector(31 downto 0);
      RD2: in std_logic_vector(31 downto 0);
      Ext_Imm: in std_logic_vector(31 downto 0);
      sa: in std_logic_vector(4 downto 0);
      func: in std_logic_vector(5 downto 0);
      PC: in std_logic_vector(31 downto 0);
      ALUSrc: in std_logic;
      ALUOp: in std_logic_vector(1 downto 0);
      ALURes: out std_logic_vector(31 downto 0);
      Branch_Address: out std_logic_vector(31 downto 0);
      Zero: out std_logic);
end EX;

architecture Behavioral of EX is

signal ALUCtrl: std_logic_vector(2 downto 0);
signal B, C: std_logic_vector(31 downto 0);

begin

process(ALUOp, func)
begin
    case ALUOp is
        when "10" =>
            case func is
                when "100000" => ALUCtrl <= "000"; -- add
                when "101010" => ALUCtrl <= "111"; -- slt
                when others => ALUCtrl <= (others => 'X');
            end case;
        when "00" => ALUCtrl <= "000"; -- lw, sw, addi
        when "01" => ALUCtrl <= "100"; -- beq
        when others => ALUCtrl <= (others => 'X');
    end case;
end process;

process(ALUCtrl)
begin
    case ALUCtrl is
        when "000" => C <= RD1 + B;
        when "100" => C <= RD1 - B;
        when "111" =>
            if signed(RD1) < signed(B) then C <= X"00000001";
            else C <= X"00000000";
            end if;
        when others => C <= (others => 'X'); 
    end case;
end process;

Zero <= '1' when C = X"00000000" else '0';

Branch_Address <= PC + (Ext_Imm(29 downto 0) & "00"); 

ALURes <= C;

process(ALUSrc)
begin
    case ALUSrc is
        when '0' => B <= RD2;
        when '1' => B <= Ext_Imm;
    end case;
end process;

end Behavioral;
